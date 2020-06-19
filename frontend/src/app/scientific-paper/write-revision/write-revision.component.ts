import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ScientificPaperService } from '../services/scientific-paper.service';

declare const Xonomy: any;

@Component({
  selector: 'app-write-revision',
  templateUrl: './write-revision.component.html',
  styleUrls: ['./write-revision.component.css']
})
export class WriteRevisionComponent implements OnInit {

  paper: string = '';
  paperId: string;
  paperSpec = {};

  constructor(private paperService: ScientificPaperService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(
      pmap => {
        this.paperId = pmap.get('id');

        this.paperService.get(this.paperId).subscribe(
          (result) => {
            this.paper = result;
            // console.log(this.paper);
            var editor = document.getElementById("editor");
            Xonomy.render(this.paper, editor, this.paperSpec);
          }
        );
      }
    );
  }

  public onFileLoad(input: any) {
    let reader = new FileReader();
    reader.onload = () => {
      this.paper = reader.result as string;
      var editor = document.getElementById("editor");
      Xonomy.render(this.paper, editor, this.paperSpec);
    };

    if (input.target.files && input.target.files.length > 0) {
      reader.readAsText(input.target.files[0]);
    }/* else {
      this.paper = '';
    } */
  }

  public submit() {
    this.paper = Xonomy.harvest("editor");

    this.paperService.revise(this.paper, this.paperId).subscribe(
      (result) => {
        this.router.navigate(['']);
      },
      (error) => {
        // this.router.navigate(['']);
      }
    );
  }

}
