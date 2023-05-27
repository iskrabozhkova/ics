import { Component, OnInit } from '@angular/core';
import { ImageService } from '../service/image.service';

@Component({
  selector: 'ics-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss'],
})
export class GalleryComponent implements OnInit {
  ngOnInit(): void {
    this.onGetImages();
  }

  constructor(private imageService : ImageService) {}

  onGetImages(): void {
    this.imageService.getImages().subscribe({
      next: (response) => console.log(response),
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }
  
}

