import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { IAssociationEntry } from 'src/app/common/shared';

@Component({
  selector: 'fc-general-new',
  templateUrl: './general-new.component.html',
  styleUrls: ['./general-new.component.scss'],
})
export class GeneralNewComponent implements OnInit {
  @Input() IsCreatePermission;
  @Input() itemForm: FormGroup;
  @Input() fields: any[];
  @Input() parentAssociations: IAssociationEntry[];
  @Input() title;
  @Input() loading;

  @Output() onPickerScroll: EventEmitter<any> = new EventEmitter();
  @Output() onAssociationOptionSelected: EventEmitter<any> = new EventEmitter();
  @Output() onSelectAssociation: EventEmitter<any> = new EventEmitter();
  @Output() onBack: EventEmitter<any> = new EventEmitter();
  @Output() onSubmit: EventEmitter<any> = new EventEmitter();

  constructor(public formBuilder: FormBuilder) {}

  ngOnInit() {}

  pickerScroll(association) {
    this.onPickerScroll.emit(association);
  }

  selectAssociation(association) {
    this.onSelectAssociation.emit(association);
  }

  associationOptionSelected($event) {
    this.onAssociationOptionSelected.emit($event);
  }

  back() {
    this.onBack.emit();
  }

  submit() {
    this.onSubmit.emit();
  }
}
