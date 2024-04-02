import { Component, Input } from "@angular/core";
import { Need } from "../need";
import { NeedService } from "../need.service";
import { LoginService } from "../login.service";

/**
 * Manager is able to make changes to need
 */
@Component({
    selector: 'app-need-edit',
    templateUrl: './need-edit.component.html',
    styleUrls: ['./need-edit.component.css']
})
export class NeedEditComponent {
    constructor(private needService: NeedService, public loginService: LoginService) {}

    @Input() currentNeed!: Need;
    errorText: string = "";

    updateNeed(): void {

    }

    changeName(newName: string): void {
        this.currentNeed.name = newName;
        this.update();
    }

    changeGoal(newGoal: number): void {
        if(newGoal < 0) {

        }
        this.currentNeed.goal = newGoal;
        this.update();
    }

    changeDescription(newDescription: string) {
        this.currentNeed.description = newDescription;
        this.update();
    }

    update(): void {
        this.needService.updateNeed(this.currentNeed).subscribe().unsubscribe();
    }
}