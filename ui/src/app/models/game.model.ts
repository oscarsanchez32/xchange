import { GameDetail } from "./GameDetail.model";

export interface Game {
    id: number;
    title: string;
    developera: string;
    shortDesc: string;
    price: number;
    imgPath: string;
    tags: string;
    gameDetail: GameDetail;
    createdAt: Date;
}