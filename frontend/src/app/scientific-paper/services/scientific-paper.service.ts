import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, } from "@angular/common/http";
import { PaperLetterDTO } from '../models/paper-letter-dto';
import { SearchMetadataDTO } from '../models/search-dto';

@Injectable({
  providedIn: 'root'
})
export class ScientificPaperService {

  private baseUrl = 'http://localhost:8081/api/paper';

  constructor(private http: HttpClient) { }

  public get(id: string) {
    return this.http.get(this.baseUrl + "/" + id, { responseType: 'text' });
  }

  public publish(dto: PaperLetterDTO) {
    return this.http.post(this.baseUrl, dto, { responseType: 'text' });
  }

  public revise(revision: string, id: string) {
    return this.http.put(this.baseUrl + "/" + id, revision, { responseType: 'text' });
  }

  public getAllByMetadata(dto: SearchMetadataDTO) {
    return this.http.post<any[]>(this.baseUrl + "/advancedSearch", dto);
  }

  public basicSearch(searchText: string) {
    return this.http.post<any[]>(this.baseUrl + "/basicSearch", searchText);
  }
  public getXML(id: string) {
    return this.http.get(this.baseUrl + "/getXMLById/" + id, { responseType: 'text' });
  }

  public getMyPapers() {
    return this.http.get<any[]>(this.baseUrl + "/getByUser");
  }

  public revokePaper(id: string) {
    return this.http.get(this.baseUrl + "/revoke/" + id);
  }

}
