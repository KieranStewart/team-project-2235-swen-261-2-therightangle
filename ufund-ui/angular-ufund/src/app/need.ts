import { Date } from "./date";
import { VolunteerDate } from "./volunteer-date";

/**
 * A way to store information about a need
 */
export interface Need {
    // Core information
    name: String;
    description: String;
    goal: number;
    progress: number;
    volunteerDates: VolunteerDate[];
    deadline: Date;
    type: String;

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
    selectedVolunteerDates: VolunteerDate[];
}