import { Date } from "./date";

/**
 * A way to store information about a need
 */
export interface Need {
    // Core information
    name: String;
    description: String;
    goal: number;
    progress: number;
    volunteerDates: Date;
    deadline: Date;

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
}