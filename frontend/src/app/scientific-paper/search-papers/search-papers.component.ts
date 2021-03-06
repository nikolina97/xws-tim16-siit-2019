import { Component, OnInit, Inject } from '@angular/core';
import { ScientificPaperService } from '../services/scientific-paper.service';
import { Router } from '@angular/router';
import { SearchMetadataDTO } from '../models/search-dto';
import {FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { saveAs } from 'file-saver';
import { AuthService } from 'src/app/user/services/auth.service';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-search-papers',
  templateUrl: './search-papers.component.html',
  styleUrls: ['./search-papers.component.css']
})
export class SearchPapersComponent implements OnInit {

  form: FormGroup;
  formBasic: FormGroup;


  displayedColumns: string[] = ['id', 'title', 'category', 'version', 'dateReceived', "state", "html", "ref", "reviews"];
  dataSource : any[] = [];
  searchDTO : SearchMetadataDTO = {
    category: null,
    title: null,
    dateRecieved : null,
    dateRevised: null,
    dateAccepted: null,
    authorFirstName: null,
    authorLastName: null,
    version: null,
    keywords: null
  }

  constructor(private paperService: ScientificPaperService, private router: Router, private fb: FormBuilder, private dialog: MatDialog,  private auth:AuthService) {
    this.form = this.fb.group({
			category: [null],
      title: [null],
      dateReceived: [null],
      dateRevised: [null],
      dateAccepted: [null],
      authorName: [null],
      version: [null],
      keywords: [null],
    });
    
    this.formBasic = this.fb.group({
      searchText: [null]
    })

   }

  ngOnInit() : void{
    this.paperService.getAllByMetadata(this.searchDTO).subscribe(
      (result) => {
        this.dataSource = result;
        console.log(this.dataSource);
      }
    )
  }

  getPapers(dto: SearchMetadataDTO) {
    this.paperService.getAllByMetadata(dto).subscribe(
      (result) => {
        this.dataSource = result;
      console.log(this.dataSource);
      }
    )
  }

  search() {
   console.log(this.form);
    if (this.form.value.category == null || this.form.value.category == "") {
      this.searchDTO.category = null;
    }else{
      this.searchDTO.category = this.form.value.category;
    }
    if (this.form.value.title == null || this.form.value.title == "") {
      this.searchDTO.title = null;
    }
    else{
      this.searchDTO.title = this.form.value.title;
    }
    if (this.form.value.dateReceived == null || this.form.value.dateReceived == "") {
      this.searchDTO.dateRecieved = null;
    }
    else{
      this.searchDTO.dateRecieved = this.form.value.dateReceived;
    }
    if (this.form.value.dateAccepted == null || this.form.value.dateAccepted == "") {
      this.searchDTO.dateAccepted = null;
    }
    else{
      this.searchDTO.dateAccepted = this.form.value.dateAccepted;
    }
    if (this.form.value.dateRevised == null || this.form.value.dateRevised == "") {
      this.searchDTO.dateRevised = null;
    }
    else{
      this.searchDTO.dateRevised = this.form.value.dateRevised;
    }
    if (this.form.value.version == null || this.form.value.version == "") {
      this.searchDTO.version = null;
    }
    else{
      this.searchDTO.version = this.form.value.version;
    }
    if (this.form.value.keywords == null || this.form.value.keywords == "") {
      this.searchDTO.keywords = null;
    }
    else{
      this.searchDTO.keywords = this.form.value.keywords;
    }
   
    let firstName = null;
    let lastName = null;
    if (this.form.value.authorName != null) {
      let split = this.form.value.authorName.split(" ");
     if (split.lenght  == 1) {
        firstName = split[0];
      }
      else {
        
        firstName = split[0];
        lastName = split[1];
      }
    }
    else if (this.form.value.authorName == "") {
    firstName = null;
      lastName = null;
    }
    this.searchDTO.authorFirstName = firstName;
    this.searchDTO.authorLastName = lastName;
    console.log(this.searchDTO);
    this.getPapers(this.searchDTO);
  }

  searchBasic() {
    this.paperService.basicSearch(this.formBasic.value.searchText).subscribe(
      (result) => {
        this.dataSource = result;
      console.log(this.dataSource);
      }
    )
  }

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

  getRef(paperId) {
    this.paperService.getReferenced(paperId).subscribe(
      (result) => {
        if (result.length == 0) {
          alert ("There are no papers that reference the given scientific paper")
        }
        else{
          this.dialog.open(RefByPopup, {
            data : result
          });
        }
      }
    )
  }
}

@Component({
  selector: 'ref-by',
  templateUrl: 'referenced-by.html',
  styleUrls: ['./search-papers.component.css']
})
export class RefByPopup {

  displayedColumns: string[] = ['id', 'title', 'category', 'dateReceived', "state"];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any[]) {}
}
