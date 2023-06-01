import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { LabelService } from '../service/label.service';
import { Label } from '../interfaces/label';

@Component({
  selector: 'ics-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {

  enteredSearchValue : string = '';
  
  options : string[] = [];


  ngOnInit(): void {
    this.labelService.getLabels().subscribe({
      next: (response: any) => {
        response.map((label: Label) => {
          this.options.push(label.name);
        });
      },
      error: (error: any) => {
        console.error('An error occurred:', error);
      },
    });
  }
  

  @Output()
    searchedTextChanged : EventEmitter<string> = new EventEmitter<string>();

  constructor(
    private labelService: LabelService,
  ) {}

  onSearchTextChanged() {
    this.searchedTextChanged.emit(this.enteredSearchValue);
  }
}
