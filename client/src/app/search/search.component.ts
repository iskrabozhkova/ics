import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'ics-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent {

  enteredSearchValue : string = '';
  
  options = ["Sam", "beth"];

  @Output()
    searchedTextChanged : EventEmitter<string> = new EventEmitter<string>();

  onSearchTextChanged() {
    this.searchedTextChanged.emit(this.enteredSearchValue);
  }
}
