import { Component, OnInit } from '@angular/core';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';

@Component({
  selector: 'ics-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss'],
})
export class GalleryComponent implements OnInit {
  images: Image[] = [];

  ngOnInit(): void {
    this.onGetImages();
  }

  constructor(private imageService : ImageService) {}

  onGetImages(): void {
    this.imageService.getImages().subscribe({
      next: (response) => {
        this.images = response;
      },
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }
  
}

