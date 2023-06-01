import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageUploadComponent } from './image-upload/image-upload.component';
import { GalleryComponent } from './gallery/gallery.component';
import { ImageDetailsComponent } from './image-details/image-details.component';
import { ErrorPageComponent } from './error-page/error-page.component';

const routes: Routes = [
  { path: '', component: ImageUploadComponent },
  { path: 'gallery', component: GalleryComponent },
  { path: 'images/:id', component: ImageDetailsComponent },
  { path: 'error', component: ErrorPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }

