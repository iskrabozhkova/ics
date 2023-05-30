import { Component, OnInit } from '@angular/core';
import { ImageService } from '../service/image.service';
import { Image } from '../interfaces/image';
import { Label } from '../interfaces/label';

@Component({
  selector: 'ics-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss'],
})
export class GalleryComponent implements OnInit {
  images: Image[] = [];

  searchText : string = '';

  ngOnInit(): void {
    this.onGetImages();
  }

  constructor(private imageService: ImageService) {}

  onGetImages(): void {
    this.imageService.getImages().subscribe({
      next: (response) => {
        this.images = response;
      },
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }

  onGetImagesWithLabels() : void {
    this.imageService.searchByLabels(this.searchText).subscribe({
      next: (response) => {
        this.images = response;
      },
      error: (error) => console.log(error),
      complete: () => console.log('Done'),
    });
  }

  onSearchTextEntered(searchValue :string) {
    this.searchText = searchValue;
    this.onGetImagesWithLabels();
  }

}
