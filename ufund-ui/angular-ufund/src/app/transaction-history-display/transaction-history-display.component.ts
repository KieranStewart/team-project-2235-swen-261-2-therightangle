import { Component, Input } from '@angular/core';
import { TransactionService } from '../transaction.service';
import { Transaction } from '../transaction';
import { Need } from '../need';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-transaction-history-display',
  templateUrl: './transaction-history-display.component.html',
  styleUrls: ['./transaction-history-display.component.css']
})
export class TransactionHistoryDisplayComponent {

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit() {
    const that = this;
    this.transactionService.getTransactions(this.displayNeed.name).subscribe({
      next(transactionArray) {
        that.transactions = transactionArray;
      }
    });
  }

  @Input() displayNeed!: Need;

  transactions!: Transaction[];


}
