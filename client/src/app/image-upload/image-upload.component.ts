import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';
import { getImageDimensions } from '../service/helpers';

@Component({
  selector: 'ics-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss'],
})
export class ImageUploadComponent {
  isLoading: boolean = false;

  errorMessage : string = '';

  uploadForm = new FormGroup({
    inputText: new FormControl('', [Validators.required]),
  });

  get inputText() {
    return this.uploadForm.get('inputText');
  }

  constructor(private imageService : ImageService, private router: Router) {}


  onUploadImage(): void {
    const url = this.uploadForm.get('inputText')!.value;
    
    getImageDimensions(url)
      .then((dimensions) => {
        this.isLoading = true;
        const image: Image = {
          url: url,
          analysisService: 'Imagga',
          date: Date().toString(),
          width: dimensions.width,
          height: dimensions.height,
        };
  
        this.imageService.uploadImage(image).subscribe({
          next: (response: any) => {
            const imageId = response.imageId;

            this.router.navigate(['/images', imageId]);
          },
          error: (error: any) => {
            this.isLoading = false;
            console.log(error);
            this.errorMessage = 'An error occurred while uploading the image. Please try again later.';
          },
          complete: () => {
            this.isLoading = false;
          },
        });
      });
  }
  
}
