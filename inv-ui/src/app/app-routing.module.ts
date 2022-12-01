import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemCategoryComponent } from './itemcategory/itemcategory.component';
import { ItemsComponent } from './items/items.component';

const routes: Routes = [
  { path: 'app-items', component: ItemsComponent },
  { path: 'app-category', component: ItemCategoryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
