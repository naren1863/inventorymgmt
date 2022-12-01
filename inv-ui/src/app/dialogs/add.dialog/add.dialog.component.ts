import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ItemsItem } from 'src/app/models/item';
import { ItemService } from 'src/app/providers/item.service';


@Component({
  selector: 'app-add.dialog',
  templateUrl: './add.dialog.component.html',
  styleUrls: ['./add.dialog.component.css']
})
export class AddDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ItemsItem,
              public itemService: ItemService) {
              
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

  public confirmAdd(): void {
    console.log("Adding Item: ", this.data);
    this.itemService.addItem(this.data);
  }

  public onCategoryChange(obj: any){
    // this.data.ItemId = obj.value.CategoryCode + '_';
    // console.log("onCategoryChange: ", this.data.ItemId);
  }

  public onHsnCodeChange(obj: any){
    // this.data.ItemId = this.data.ItemId + obj.value.HsnCode;
    // console.log("onHsnCodeChange: ", this.data.ItemId);
  }
}


