import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Location } from '@angular/common';

import { TestingModule } from 'src/testing/utils';
import { RegisterComponent } from './register.component';
import { RegisterService } from './register.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let el: HTMLElement;

  let relationData: any = {};
  let data = {
    emailAddress: 'emailAddress1@test.com',
    firstName: 'firstName1',
    id: 1,
    isActive: true,
    lastName: 'lastName1',
    password: 'password1',
    confirmPassword: 'password1',
    phoneNumber: 'phoneNumber1',
    userName: 'userName1',
    ...relationData,
  };

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [RegisterComponent],
        imports: [TestingModule],
        providers: [RegisterService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RegisterComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should run #ngOnInit()', async () => {
      component.ngOnInit();
      expect(component.itemForm).toBeDefined();
    });

    it('should run onSubmit and handle success response', async () => {
      const router = TestBed.get(Router);
      const location = TestBed.get(Location);
      let navigationSpy = spyOn(router, 'navigate').and.returnValue(of(true));

      component.itemForm.patchValue(data);
      component.itemForm.enable();
      fixture.detectChanges();

      spyOn(component.registerService, 'register').and.returnValue(of(true));
      el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
      el.click();

      expect(component.registerService.register).toHaveBeenCalled();
      expect(component.loading).toBe(false);
      let responsePromise = navigationSpy.calls.mostRecent().returnValue;
      await responsePromise;
      expect(router.navigate).toHaveBeenCalledWith(['register-complete'], {
        queryParams: { email: data.emailAddress },
      });
    });

    it('should run onSubmit and handle error response', async () => {
      spyOn(component.errorService, 'showError').and.returnValue();

      component.itemForm.patchValue(data);
      component.itemForm.enable();
      fixture.detectChanges();

      spyOn(component.registerService, 'register').and.callFake(() => {
        return throwError('error occurred');
      });
      el = fixture.debugElement.query(By.css('button[name=save]')).nativeElement;
      el.click();

      expect(component.errorService.showError).toHaveBeenCalled();
      expect(component.registerService.register).toHaveBeenCalled();
      expect(component.loading).toBe(false);
    });
  });
});
