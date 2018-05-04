export interface WSUPContextDeclarations {
    login: string;
    register: string;
    friends: string;
    users: string;
}

export const wsupContexts: WSUPContextDeclarations = {
    login: "LOGIN",
    register: "REGISTER",
    friends: "FRIENDS",
    users: "USERS"
}

export enum WSUPRequestType {
    CREATE,
    READ,
    UPDATE,
    DELETE
}

export class WSUPRequest {

    type: WSUPRequestType;
    context: string;
    body: any;

    public static build(type: WSUPRequestType, context: string, body: any): WSUPRequest {
        var req = new WSUPRequest();
        req.type = type;
        req.context = context;
        req.body = body;
        return req;
    }

    public toString(): string {
        var type = WSUPRequestType[this.type];
        return `REQUEST ${type}\n${this.context}\n${JSON.stringify(this.body)}`;
    }

    public toActionString(): string {
        var type = WSUPRequestType[this.type];
        return `${type}/${this.context}`;
    }

}