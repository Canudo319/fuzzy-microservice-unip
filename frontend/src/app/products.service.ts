import { Injectable } from '@angular/core';
import { catchError, EMPTY, map, Observable } from 'rxjs';
import { Products } from './products.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  url_docker = "http://localhost:8081";

  url = this.url_docker;

  products: any[] = [];

  constructor(private http: HttpClient) { }
  create(events: [Products]): Observable<Products> {
    return this.http.post<Products>(this.url + 's/shoppingItems', events).pipe(map(obj => obj),
      catchError(e => this.handleWithError(e)));
  }

  delete(id: any): Observable<Products> {
    return this.http.delete<Products>(this.url + "/shoppingItem/" + id).pipe(map(obj => obj),
      catchError(e => this.handleWithError(e)));
  }

  read(): Observable<Products[]> {
    return this.http.get<Products[]>(this.url + '/shoppingItem');
  }

  readById(id: any): Observable<Products[]> {
    return this.http.get<Products[]>(`${this.url}/shoppingItem/${id}`);
  }

  readStockById(id: number): Observable<Products[]> {
    let params = new HttpParams();
    let reqRet: any = "";
    return this.http.get<Products[]>(`${this.url}/stock/item/${id}`, { observe: "response", params }).pipe(
      map(response => {
        reqRet = response.body;
        return reqRet;
      }));
  }

  readBestItens(preferedItem: number, lat: number, long: number): Observable<Products[]> {
    let params = new HttpParams();
    let reqRet: any = "";
    params = params.append("item", preferedItem);
    params = params.append("latitude", lat);
    params = params.append("longitude", long);

    return this.http.get<Products[]>(`${this.url}/stock/bestItens`, { observe: "response", params }).pipe(
      map(response => {
        reqRet = response.body;
        return reqRet;
      }));
  }

  readCheapestItems(preferedItem: number): Observable<Products[]> {
    let params = new HttpParams();
    let reqRet: any = "";
    params = params.append("item", preferedItem);

    return this.http.get<Products[]>(`${this.url}/stock/cheapestItems`, { observe: "response", params }).pipe(
      map(response => {
        reqRet = response.body;
        return reqRet;
      }));
  }

  readNearestSupplier(preferedItem: number, lat: number, long: number): Observable<Products[]> {
    let params = new HttpParams();
    let reqRet: any = "";
    params = params.append("item", preferedItem);
    params = params.append("latitude", lat);
    params = params.append("longitude", long);

    return this.http.get<Products[]>(`${this.url}/stock/nearestSupplier`, { observe: "response", params }).pipe(
      map(response => {
        reqRet = response.body;
        return reqRet;
      }));
  }

  update(id: any, object: any): Observable<Products> {
    return this.http.put<Products>(this.url + "/" + id, object).pipe(map(obj => obj),
      catchError(e => this.handleWithError(e)));
  }

  handleWithError(error: any): Observable<any> {
    console.log("unknown error");
    console.log(error);
    return EMPTY;
  }

  filter(filters: string, arrProducts: any[]) {
    let filteredItems: any[] = arrProducts;

    Object.keys(filters).forEach((filter: any) => {
      filteredItems = filteredItems.filter((register: any) => {
        if (typeof register[filter] === 'string') {
          return register[filter].toLocaleLowerCase().includes(filters[filter].toLocaleLowerCase());
        } else {
          return register[filter] === filters[filter];
        }
      });
    });

    return filteredItems;
  }

  resetFilters(arrProducts: any[]) {
    return arrProducts;
  }
}