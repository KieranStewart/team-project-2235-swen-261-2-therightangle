import { Date } from "./date";
import { Tag } from "./tag"

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
    type: String;
    tags: Tag;

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
}