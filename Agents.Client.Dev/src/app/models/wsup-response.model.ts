import { WSUPRequestType } from "./wsup-request.model";

export enum WSUPResponseType {
    SUCCESS,
    FAILURE
}

export class WSUPResponse {

    type: WSUPRequestType;
    response: WSUPResponseType;
    context: string;
    data: any;

    public static parse(data: string): WSUPResponse {
        var response = new WSUPResponse();
        var messageSplit = data.split("\n", 3);
        if (messageSplit.length != 3)
            return null;
        var headerSplit = messageSplit[0].split(" ", 3);
        if (headerSplit.length != 3)
            return null;
        if (headerSplit[0] != "RESPONSE")
            return null;
        var type: WSUPRequestType = WSUPRequestType[headerSplit[1]];
        if (typeof type == 'undefined')
            return null;
        response.type = type;
        var responseType: WSUPResponseType = WSUPResponseType[headerSplit[2]];
        if (typeof responseType == 'undefined')
            return null;
        response.response = responseType;
        response.context = messageSplit[1];
        var responseData = null;
        try {
            responseData = JSON.parse(messageSplit[2]);
        } catch (error) {

        }
        response.data = responseData;
        return response;
    }

    public toActionString(): string {
        var type = WSUPRequestType[this.type];
        return `${type}/${this.context}`;
    }

}