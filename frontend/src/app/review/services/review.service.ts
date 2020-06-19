import { Injectable } from '@angular/core';
import { HttpClient, } from "@angular/common/http";
import { ReviewDTO } from '../models/review-dto';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private baseUrl = 'http://localhost:8081/api/review';

  constructor(private http: HttpClient) { }

  public post(dto: ReviewDTO) {
    return this.http.post(this.baseUrl, dto, { responseType: 'text' });
  }

  public getRecommendedReviewers(paperId: string) {
    return this.http.get<any[]>(this.baseUrl + "/recommended/" + paperId);
  }

  public addReviewer(paperId: string, email: string) {
    return this.http.post(this.baseUrl + "/assignReviewer/" + paperId, email);
  }
}
