import { Component, Input, OnInit } from "@angular/core";
import { Need } from "../need";
import { NeedService } from "../need.service";
import { take } from "rxjs";
import { CupboardComponent } from "../cupboard/cupboard.component";
import { NeedCacheService } from "../need-cache.service";
import { TransactionService } from "../transaction.service";
import { TransactionHistoryDisplayComponent } from "../transaction-history-display/transaction-history-display.component";

/**
 * Manager is able to make changes to need
 */
@Component({
    selector: 'app-need-edit',
    templateUrl: './need-edit.component.html',
    styleUrls: ['./need-edit.component.css']
})
export class NeedEditComponent {

    validTypes: String[] = ["donation", "volunteer"];
    private rollbackNeed!: Need;
    newNeedName: string = "";
    @Input() currentNeed!: Need | null;
    errorText: string = "";
    createErrorText: string = "";

    constructor(private needService: NeedService, 
        private cupboardComponent: CupboardComponent, 
        private needCacheService: NeedCacheService, 
        private transactionService: TransactionService, 
        private transactionComponent: TransactionHistoryDisplayComponent) {}

    ngOnChanges() {
        // Fallback code for if something is null in the backend
        if(this.currentNeed != null) {
            this.rollbackNeed = JSON.parse(JSON.stringify(this.currentNeed));
            if(this.currentNeed.deadline == null) {
                this.currentNeed.deadline = {day: 0, month: 0, year: 0};
            }
            if(this.currentNeed.description == null) {
                this.currentNeed.description = "";
            }
            if(this.currentNeed.volunteerDates == null) {
                this.currentNeed.volunteerDates = [];
            }
            if(this.currentNeed.type == null) {
                this.currentNeed.type = "donation";
            }
        }
    }

    updateNeed(): void {
        if(this.currentNeed == null) {
            this.errorText = "An error occurred, try reselecting your need."
            return;
        }
        const that = this;
        this.errorText = "Loading...";
        if(this.validateNeed()) {
            this.needService.updateNeed(this.currentNeed).pipe(take(1)).subscribe({
                next(value) {
                    if(value == null) {
                        that.errorText = "There was an error updating this need.";
                    } else {
                        that.errorText = "Successfully updated!";
                        that.cupboardComponent.getCupboard();
                    }
                }
            });
        }
    }

    private validateNeed(): boolean {
        if(this.currentNeed == null) {
            return false;
        }
        if(this.currentNeed.name.length == 0) {
            this.errorText = "Please enter a name";
            return false;
        }
        if(this.currentNeed.deadline.day <= 0 || this.currentNeed.deadline.day > 31) {
            this.errorText = "Invalid day";
            return false;
        }
        if(this.currentNeed.deadline.month <= 0 || this.currentNeed.deadline.month > 12) {
            this.errorText = "Invalid month";
            return false;
        }
        if(this.currentNeed.deadline.year == 0) {
            this.errorText = "Please check your year";
            return false;
        }
        if(!this.validTypes.includes(this.currentNeed.type)) {
            this.errorText = "Invalid need type";
            return false;
        }

        if(this.currentNeed.volunteerDates.length > 0 && this.currentNeed.type == this.validTypes[0]) {
            this.currentNeed.volunteerDates = [];
        }
        if(this.currentNeed.goal != 0 && this.currentNeed.type == this.validTypes[1]) {
            this.currentNeed.goal = 0;
        }
        if(this.currentNeed.volunteerDates.length == 0 && this.currentNeed.type == this.validTypes[1]) {
            this.errorText = "There are no volunteer dates for a volunteer based need";
            return false;
        }

        if(this.currentNeed.goal < this.currentNeed.progress) {
            this.errorText = "Goal is less than the amount already collected";
            return false;
        }

        for (let index = 0; index < this.currentNeed.volunteerDates.length; index++) {
            const element = this.currentNeed.volunteerDates[index];
            if(element.day <= 0 || element.day > 31) {
                this.errorText = "Invalid day for volunteer date number " + (index + 1);
                return false;
            }
            if(element.month <= 0 || element.month > 12) {
                this.errorText = "Invalid month for volunteer date number " + (index + 1);
                return false;
            }
            if(element.year == 0) {
                this.errorText = "Please check your year for volunteer date number " + (index + 1);
                return false;
            }
        }

        return true;
    }

    addNewVolunteerDate(): void {
        if(this.currentNeed == null) {
            this.errorText = "An error occurred";
            return;
        }
        this.currentNeed.volunteerDates.push({day: 0, month: 0, year: 0, filled:true});
    }

    removeVolunteerDate(): void {
        if(this.currentNeed == null) {
            this.errorText = "An error occurred";
            return;
        }
        this.currentNeed.volunteerDates.pop();
    }

    discardChanges(askForConfirmation: boolean): void {
        if(this.currentNeed == null) {
            this.errorText = "An error occurred";
            return;
        }
        if(askForConfirmation) {
            if(!confirm("Are you sure you want to discard all changes to this need?")) {
                return;
            }
        }
        this.currentNeed.deadline = this.rollbackNeed.deadline;
        this.currentNeed.description = this.rollbackNeed.description;
        this.currentNeed.donationAmount = this.rollbackNeed.donationAmount;
        this.currentNeed.goal = this.rollbackNeed.goal;
        this.currentNeed.name = this.rollbackNeed.name;
        this.currentNeed.type = this.rollbackNeed.type;
        this.currentNeed.volunteerDates = this.rollbackNeed.volunteerDates;
    }

    deleteNeed(): void {
        if(this.currentNeed == null) {
            this.errorText = "An error occurred";
            return;
        }
        
        const that = this;
        if(this.rollbackNeed.name == this.currentNeed.name) {
            if(!confirm("Are you sure you want to first discard changes to this need, then delete this need (cannot be undone)?")) {
                return;
            }
            this.discardChanges(false);
        } else {
            if(!confirm("Are you sure you want to delete this need (cannot be undone)?")) {
                return;
            }
        }

        this.needService.deleteNeed(this.currentNeed.name).pipe(take(1)).subscribe({
            next(value) {
                that.cupboardComponent.getCupboard();
                that.needCacheService.selectedNeed = null;
            },
        });
    }

    createNeed(newName: string): void {
        if(newName == null || newName == "") {
            this.createErrorText = "Please enter a name for the new need."
            return;
        }
        let need: Need = {
            name: newName,
            description: "",
            goal: 0,
            progress: 0,
            volunteerDates: [],
            deadline: {
                month: 0,
                day: 0,
                year: 0
            },
            type: "donation",
            tags: ["admin"],
            inFundingBasket: false,
            donationAmount: 0,
            selectedVolunteerDates: []
        };

        const that = this;
        this.needService.addNeed(need).pipe(take(1)).subscribe({
            next(value) {
                that.newNeedName = "";
                if(value == null) {
                    that.createErrorText = "Need creation failure: that name is already taken";
                } else {
                    that.createErrorText = "Need created successfully! You can find and edit it in the cupboard";
                    that.cupboardComponent.getCupboard();
                }
            },
        });
    }

    deleteTransactionHistory(): void {
        if(this.currentNeed == null) {
            return;
        }
        if(confirm("Are you sure you want to delete the transaction history for this need (cannot be undone)?")) {
            const that = this;
            this.transactionService.getTransactionsNo404(this.currentNeed.name).pipe(take(1)).subscribe({
                next(value) {
                    if(value == null || value.length == 0) {
                        that.errorText = "No transactions to delete!";
                    } else {
                        if(that.currentNeed == null) return;
                        that.transactionService.deleteTransactionsFor(that.currentNeed.name).pipe(take(1)).subscribe({
                            next(_) {
                                that.cupboardComponent.getCupboard();
                                that.transactionComponent.update();
                                that.errorText = "Successfully deleted transactions";
                            },
                        });
                    }
                },
            })
            
        }
    }

}