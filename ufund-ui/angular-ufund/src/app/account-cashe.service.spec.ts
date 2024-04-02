import { TestBed } from '@angular/core/testing';

import { AccountCasheService } from './account-cashe.service';

describe('AccountCasheService', () => {
  let service: AccountCasheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountCasheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
