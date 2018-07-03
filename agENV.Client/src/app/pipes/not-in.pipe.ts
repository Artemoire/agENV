import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'notIn',
  pure: false
})
export class NotInPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value || !args)
      return null;
      let argsList = args as any[];
    let values = value as any[];
    return values.filter(x=>argsList.indexOf(x) == -1);
  }

}
