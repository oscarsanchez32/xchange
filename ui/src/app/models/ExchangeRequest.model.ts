import { ExchangeStatus } from "./ExchangeStatus.enum";

export interface ExchangeRequest {
     id: number;
     exchangeOpener: string;
     exchangeCloser: string;
     openerOwnedGameId: number;
     openerOwnedGameTitle: string;
     openerExchangeGameId: number;
     openerExchangeGameTitle: String;
     exchangeStatus: ExchangeStatus;
     createdAt: Date;
     closedAt: Date;
     canExchange: boolean;
}