/// <reference path="../typings/index.d.ts" />
import * as encoding from 'text-encoding';

export interface ExampleModel {
  message: string;
}

export class ExampleMessageCodec {

  static resultSelector(event: MessageEvent): ExampleModel {
    if (event.data instanceof ArrayBuffer) {
      return ExampleMessageCodec.readBinary(event.data);
    }
    return ExampleMessageCodec.readText(event.data);
  }

  static textDecoder = new encoding.TextDecoder('UTF-8');
  static textEncoder = new encoding.TextEncoder();

  static readBinary(data: ArrayBuffer): ExampleModel {
    return {message: ExampleMessageCodec.textDecoder.decode(new DataView(data))};
  }

  static writeBinary(entity: ExampleModel): ArrayBufferView | Blob {
    return ExampleMessageCodec.textEncoder.encode(entity.message);
  }

  static readText(text: string): ExampleModel {
    return JSON.parse(text);
  }

  static writeText(entity: ExampleModel): string {
    return JSON.stringify(entity);
  }
}
