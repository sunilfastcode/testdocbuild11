import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { LanguageService } from '../language.service';
import { ILanguage } from '../ilanguage';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

@Component({
  selector: 'app-language-new',
  templateUrl: './language-new.component.html',
  styleUrls: ['./language-new.component.scss'],
})
export class LanguageNewComponent extends BaseNewComponent<ILanguage> implements OnInit {
  title: string = 'New Language';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<LanguageNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public languageService: LanguageService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      dialogRef,
      data,
      global,
      pickerDialogService,
      languageService,
      errorService
    );
  }

  ngOnInit() {
    this.entityName = 'Language';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
    this.setPickerSearchListener();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      lastUpdate: ['', Validators.required],
      lastUpdateTime: ['12:00 AM', Validators.required],
      name: ['', Validators.required],
    });

    this.fields = [
      {
        name: 'lastUpdate',
        label: 'last Update',
        isRequired: true,
        isAutoGenerated: false,
        type: 'date',
      },
      {
        name: 'lastUpdateTime',
        label: 'last Update Time',
        isRequired: true,
        isAutoGenerated: false,
        type: 'time',
      },
      {
        name: 'name',
        label: 'name',
        isRequired: true,
        isAutoGenerated: false,
        type: 'string',
      },
    ];
  }

  setAssociations() {
    this.associations = [];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let language = this.itemForm.getRawValue();
    language.lastUpdate = this.dataService.combineDateAndTime(language.lastUpdate, language.lastUpdateTime);
    delete language.lastUpdateTime;
    super.onSubmit(language);
  }
}
