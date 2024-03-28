import { Date } from "./date";

/**
 * Stores information about transactions
 */
export interface Transaction {
    // Core information
    amount: number;
    needName: string;

    // Information set by the backend only
    id: number;
    timestamp: Date;
}