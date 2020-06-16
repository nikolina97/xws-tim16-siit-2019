import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { PaperLetterDTO } from '../models/paper-letter-dto';

@Injectable({
  providedIn: 'root'
})
export class ScientificPaperService {

  private baseUrl = 'http://localhost:8081/api/paper';

  constructor(private http: HttpClient) { }

  public publish(dto: PaperLetterDTO) {
    return this.http.post(this.baseUrl, dto);
  }

}
