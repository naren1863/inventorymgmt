import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {FormControl, Validators} from '@angular/forms';
import { ItemService } from 'src/app/providers/item.service';
import { Uom } from '../../models/uom';
import { Brand } from 'src/app/models/brand';
import { ItemCategory } from 'src/app/models/itemCategory';
import { HsnCode } from 'src/app/models/hsnCode';

@Component({
  selector: 'app-update.dialog',
  templateUrl: './update.dialog.component.html',
  styleUrls: ['./update.dialog.component.css']
})
export class UpdateDialogComponent  {

  constructor(public dialogRef: MatDialogRef<UpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public itemService: ItemService) {
                if(this.data.Uom == null)
                  this.data.Uom = new Uom('', '');  

                  if(this.data.Brand == null)
                  this.data.Brand = new Brand('', '');  
                  
                  if(this.data.Category == null)
                  this.data.Category = new ItemCategory('', '');  

                  if(this.data.HsnCode == null)
                  this.data.HsnCode = new HsnCode('', '', 0);  
               }

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Required field' :
      this.formControl.hasError('email') ? 'Not a valid email' :
        '';
  }

  submit() {
    // emppty stuff
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  editItem(): void {
    this.itemService.updateItem(this.data);
  }
}
