import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Label } from '../interfaces/label';

@Injectable({
  providedIn: 'root',
})
export class LabelService {

  private apiUrl = 'http://localhost:8080/api/tags';

  constructor(private http : HttpClient) {}

  getLabels() : Observable<Label[]> {
    return this.http.get<Label[]>(this.apiUrl);
  }
}
