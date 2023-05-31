import { Component, OnInit } from '@angular/core';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';
import { Label } from '../interfaces/label';
import { Router } from '@angular/router';

@Component({
  selector: 'ics-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss'],
})
export class GalleryComponent implements OnInit {
  images: Image[] = [];

  searchText: string = '';

  basic = false;

  remainingLabels?: Label[] = [];

  ngOnInit(): void {
    this.onGetImages();
  }

  constructor(private imageService: ImageService, private router: Router) {}

  onGetImages(): void {
    this.imageService.getImages().subscribe({
      next: (response) => {
        this.images = response;
      },
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }

  onGetImagesWithLabels(): void {
    this.imageService.searchByLabels(this.searchText).subscribe({
      next: (response) => {
        this.images = response;
      },
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }

  onSearchTextEntered(searchValue: string) {
    this.searchText = searchValue;
    this.onGetImagesWithLabels();
  }

  showRemainingLabels(labels?: Label[]): void {
    this.remainingLabels = labels;
    this.basic = true;
  }

  showFullInfo(imageId?: number) {
    this.router.navigate(['/images', imageId]);
  }

  deleteImage(imageId?: number) {
    this.imageService.deleteImage(imageId).subscribe({
      next: (response) => {
        this.images = [...this.images.filter((image) => image.imageId !== imageId)];
        console.log(this.images);
        console.log(response);
      },
      error: (error) => console.log(error),
      complete: () => console.log('Deleting done'),
    });
  }
}
