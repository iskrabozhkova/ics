// eslint-disable-next-line import/extensions
import '@cds/core/icon/register.js';
import { ClarityIcons, userIcon } from '@cds/core/icon';
import { Injectable } from '@angular/core';

@Injectable(
  {
    providedIn: 'root',
  },
)
export class IconsService {
  public load(): void {
    ClarityIcons.addIcons(userIcon);
  }
}