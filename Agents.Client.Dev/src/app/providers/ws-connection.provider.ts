import { environment } from '../../environments/environment';

export class WSConnectionProvider {
    static connect(): WebSocket {
        return new WebSocket(environment.wsEndpoint);
    }
}