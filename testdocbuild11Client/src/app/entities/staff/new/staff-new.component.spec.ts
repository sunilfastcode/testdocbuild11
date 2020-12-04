import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { By } from '@angular/platform-browser';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';

import { TestingModule, EntryComponents, checkValues } from 'src/testing/utils';
import { IStaff, StaffService, StaffNewComponent } from '../';
import { DateUtils } from 'src/app/common/shared';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('StaffNewComponent', () => {
  let component: StaffNewComponent;
  let fixture: ComponentFixture<StaffNewComponent>;

  let el: HTMLElement;

  let d = new Date();
  let t = DateUtils.formatDateStringToAMPM(d);

  let relationData: any = {
    addressId: 1,
    addressDescriptiveField: 1,
  };
  let data: IStaff = {
    active: true,
    email: 'email1',
    firstName: 'firstName1',
    lastName: 'lastName1',
    lastUpdate: d,
    lastUpdateTime: t,
    password: 'password1',
    staffId: 1,
    storeId: 1,
    username: 'username1',
    ...relationData,
  };

  let formData = { ...data };
  delete formData.staffId;
  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StaffNewComponent, NewComponent, FieldsComp],
        imports: [TestingModule],
        providers: [StaffService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StaffNewComponent);
      component = fixture.componentInstance;
      spyOn(component, 'manageScreenResizing').and.returnValue();
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should run #ngOnInit()', async(() => {
      component.ngOnInit();

      expect(component.title.length).toBeGreaterThan(0);
      expect(component.associations).toBeDefined();
      expect(component.parentAssociations).toBeDefined();
      expect(component.itemForm).toBeDefined();
    }));

    it('should run #onSubmit()', async () => {
      component.itemForm.patchValue(data);
      component.itemForm.enable();
      fixture.detectChanges();
      spyOn(component, 'onSubmit').and.returnValue();
      el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
      el.click();
      expect(component.onSubmit).toHaveBeenCalled();
    });

    it('should call the cancel', async () => {
      spyOn(component, 'onCancel').and.callThrough();
      el = fixture.debugElement.query(By.css('a[name=cancel]')).nativeElement;
      el.click();
      expect(component.onCancel).toHaveBeenCalled();
    });
  });

  describe('Integration tests', () => {
    // had to create a different suite because couldn't override MAT_DIALOG_DATA provider
    describe('', () => {
      it('should set the passed data to form', async () => {
        TestBed.configureTestingModule({
          declarations: [StaffNewComponent, NewComponent, FieldsComp].concat(EntryComponents),
          imports: [TestingModule],
          providers: [StaffService, { provide: MAT_DIALOG_DATA, useValue: relationData }],
        });
        TestBed.overrideProvider(MAT_DIALOG_DATA, { useValue: relationData });
        fixture = TestBed.createComponent(StaffNewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();

        component.checkPassedData();
        fixture.detectChanges();
        expect(checkValues(component.itemForm.getRawValue(), relationData)).toBe(true);
      });
    });

    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [StaffNewComponent, NewComponent, FieldsComp].concat(EntryComponents),
          imports: [TestingModule],
          providers: [StaffService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        });
      }));

      beforeEach(() => {
        fixture = TestBed.createComponent(StaffNewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

      it('should create', () => {
        expect(component).toBeTruthy();
      });

      it('should run #ngOnInit()', async(() => {
        component.ngOnInit();

        expect(component.title.length).toBeGreaterThan(0);
        expect(component.associations).toBeDefined();
        expect(component.parentAssociations).toBeDefined();
        expect(component.itemForm).toBeDefined();
        expect(component.data).toEqual({});
      }));

      it('should create the record and close the dialog with created object response', async () => {
        component.itemForm.patchValue(data);
        component.itemForm.enable();
        fixture.detectChanges();
        spyOn(component.dialogRef, 'close').and.returnValue();
        spyOn(component.dataService, 'create').and.returnValue(of(data));

        el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
        el.click();
        expect(component.dialogRef.close).toHaveBeenCalledWith(data);
      });

      it('should close the dialog with null data when cancel button is pressed', async () => {
        spyOn(component.dialogRef, 'close').and.returnValue();
        el = fixture.debugElement.query(By.css('a[name=cancel]')).nativeElement;
        el.click();
        expect(component.dialogRef.close).toHaveBeenCalledWith(null);
      });
    });
  });
});
