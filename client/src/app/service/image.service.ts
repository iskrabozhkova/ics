import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Image } from '../interfaces/image';

@Injectable({
  providedIn: 'root',
})
export class ImageService {

  private apiUrl = 'http://localhost:8080/api/images';

  constructor(private http : HttpClient) {}

  getImages() : Observable<Image[]> { 
    return this.http.get<Image[]>(this.apiUrl);
  }

  getApiUrl() : string {
    return this.apiUrl;
  }
  
  uploadImage(image : Image) : Observable<Image> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.post<Image>(this.apiUrl, image, { headers: headers });
  }

  getImageById(imageId : string): Observable<Image> {
    return this.http.get<Image>(`${ this.apiUrl }/${ imageId }`);
  }

  searchByLabels(labels : string) : Observable<Image[]> {
    const url = `${ this.apiUrl }?labels=${ labels }`;

    return this.http.get<Image[]>(url);
  }

  deleteImage(imageId? : number) : Observable<string> {
    return this.http.delete(`${ this.apiUrl }/${ imageId }`, { responseType: 'text' });
  }
}
