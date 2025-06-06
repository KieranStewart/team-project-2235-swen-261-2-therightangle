import { Component, Input } from '@angular/core';
import { TransactionService } from '../transaction.service';
import { Transaction } from '../transaction';
import { Need } from '../need';
import { take } from 'rxjs';

@Component({
  selector: 'app-transaction-history-display',
  templateUrl: './transaction-history-display.component.html',
  styleUrls: ['./transaction-history-display.component.css']
})
export class TransactionHistoryDisplayComponent {

  constructor(private transactionService: TransactionService) {
  }

  ngOnChanges() {
    this.update();
  }

  update(): void {
    if(this.displayNeed == null || this.displayNeed == undefined) {
      this.transactions = [];
      return;
    }
    const that = this;
    this.transactionService.getTransactionsNo404(this.displayNeed.name).pipe(take(1)).subscribe({
      next(transactionArray) {
        that.transactions = transactionArray;
      }
    });
  }

  @Input() displayNeed!: Need | null;

  transactions!: Transaction[];


}
