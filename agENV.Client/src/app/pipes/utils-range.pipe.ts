import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'utilsRange'
})
export class UtilsRangePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (args)
      return new Array(args).fill(0);
      
    return [];
  }

}
