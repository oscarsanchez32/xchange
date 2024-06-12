import { GameDetail } from "./GameDetail.model";

export interface GameList {
    id: number;
    title: string;
    developera: string;
    shortDesc: string;
    price: number;
    imgPath: string;
    gameDetail: GameDetail;
    createdAt: Date;
    timesPurchased: number;
    avgReviews: number;
    reviewCount: number;
    productPurchased: boolean;
    tags: string;
}