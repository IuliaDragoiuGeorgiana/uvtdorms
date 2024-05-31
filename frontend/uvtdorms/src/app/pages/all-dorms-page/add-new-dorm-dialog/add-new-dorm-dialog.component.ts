import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-add-new-dorm-dialog',
  templateUrl: './add-new-dorm-dialog.component.html',
  styleUrl: './add-new-dorm-dialog.component.css',
})
export class AddNewDormDialogComponent {
  @Output() dialogVisibilityChanged: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  public toggleDialogVisibility(): void {
    this.dialogVisibilityChanged.emit(false);
  }
}
