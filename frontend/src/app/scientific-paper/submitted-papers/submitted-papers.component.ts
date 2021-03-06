import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from '../services/scientific-paper.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-submitted-papers',
  templateUrl: './submitted-papers.component.html',
  styleUrls: ['./submitted-papers.component.css']
})
export class SubmittedPapersComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'category', 'version', 'dateReceived', "state", "paper", "letter", "reviewers", "accept", "reject"];
  dataSource : any[] = [];

  constructor(private paperService: ScientificPaperService) { }

  ngOnInit() {
    this.getPapers();
  }

  getPapers() {
    this.paperService.submittedPapers().subscribe(
      (result) => {
        this.dataSource = result;
      console.log(this.dataSource);
      }
    )
  }

  accept(spId: string) {
    this.paperService.publishPaper(spId).subscribe(
      result => {
        if(result==false){
          alert("Document can't be published!");
          this.getPapers();
        }
      },
      error => { console.log(error.error()) }
    )
  };

  reject(spId: string) {
    this.paperService.rejectPaper(spId).subscribe(
      result => { 
        if(result==false){
        alert("Document can't be rejected!");
        this.getPapers();
      }
     },
      error => { console.log(error.error()) }
    )
  };

  download(id: string){
    this.paperService.getXML(id).subscribe(
      data => 
      { console.log(data);
        this.downloadFile(data);
      },
      error =>{
        console.log(error.error);
      }
    )
  }

  downloadFile(data) {
    const blob = new Blob([data], { type: 'text/xml' });
    const url= window.URL.createObjectURL(blob);
    saveAs(url, "scientific_paper.xml");
    window.open(url);
  }
}
