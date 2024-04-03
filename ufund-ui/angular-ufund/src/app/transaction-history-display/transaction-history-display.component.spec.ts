import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionHistoryDisplayComponent } from './transaction-history-display.component';

describe('TransactionHistoryDisplayComponent', () => {
  let component: TransactionHistoryDisplayComponent;
  let fixture: ComponentFixture<TransactionHistoryDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionHistoryDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionHistoryDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
