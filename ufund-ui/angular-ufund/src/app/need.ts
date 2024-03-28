import { Date } from "./date";

/**
 * A way to store information about a need
 */
export interface Need {
    // Core information
    name: string;
    description: string;
    goal: number;
    progress: number;
    volunteerDates: Date;
    deadline: Date;
    type: string;

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
}