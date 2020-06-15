import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CoverLetterDTO } from '../models/cover-letter-dto';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {

  private baseUrl = 'http://localhost:8081/api/letter';

  constructor(private http: HttpClient) { }

  public post(dto: CoverLetterDTO) {
    return this.http.post(this.baseUrl, dto);
  }

}
