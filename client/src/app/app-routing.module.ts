import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageUploadComponent } from './image-upload/image-upload.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ImageDetailsComponent } from './image-details/image-details.component';

const routes: Routes = [
  { path: '', component: ImageUploadComponent },
  { path: 'gallery', component: GalleryComponent },
  { path: 'images/:id', component: ImageDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }

