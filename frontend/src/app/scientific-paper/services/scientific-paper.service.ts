import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { PaperLetterDTO } from '../models/paper-letter-dto';
import { SearchMetadataDTO } from '../models/search-dto';

@Injectable({
  providedIn: 'root'
})
export class ScientificPaperService {

  private baseUrl = 'http://localhost:8081/api/paper';

  constructor(private http: HttpClient) { }

  public publish(dto: PaperLetterDTO) {
    return this.http.post(this.baseUrl, dto);
  }

  public getAllByMetadata(dto : SearchMetadataDTO) {
    return this.http.post<any[]>(this.baseUrl + "/advancedSearch", dto);
  }

}
