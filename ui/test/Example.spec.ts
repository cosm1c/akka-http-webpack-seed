/// <reference path="../typings/index.d.ts" />
import {ExampleMessageCodec} from '../app/Example';

describe('ExampleMessageCodec', () => {

  it('Reads Text', () => {
    let exampleModel = ExampleMessageCodec.readText('{"message":"helloWorld"}');

    expect(exampleModel).toEqual({message: 'helloWorld'});
  });
});
