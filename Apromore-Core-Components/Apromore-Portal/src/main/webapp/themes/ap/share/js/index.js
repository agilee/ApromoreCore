Ap.share = Ap.share || {}
Ap.share.editUser = (userName, index) => {
  zAu.send(new zk.Event(zk.Widget.$('$userEditBtn'), 'onExecute', userName));
}

Ap.share.editGroup = (groupName, index) => {
  zAu.send(new zk.Event(zk.Widget.$('$groupEditBtn'), 'onExecute', groupName));
}

Ap.share.changeGroupNameOK = (groupName, rowGuid) => {
  zAu.send(new zk.Event(zk.Widget.$('$groupEditBtn'), 'onChangeNameOK', { groupName, rowGuid }));
}

Ap.share.changeGroupNameCancel = (groupName, rowGuid) => {
  zAu.send(new zk.Event(zk.Widget.$('$groupEditBtn'), 'onChangeNameCancel', { groupName, rowGuid }));
}

Ap.share.tbFocus = (el) => {
  jq(el).next().css('visibility', 'visible');
  jq(el).next().next().css('visibility', 'visible');
  // zk.$(jq(el).next()[0]).setVisible(true);
  // zk.$(jq(el).next().next()[0]).setVisible(true);
}

Ap.share.tbBlur = (el) => {
  setTimeout(
      function () {
        jq(el).next().css('visibility', 'hidden');
        jq(el).next().next().css('visibility', 'hidden');
      }
      , 1000);
  // zk.$(jq(el).next()[0]).setVisible(false);
  // zk.$(jq(el).next().next()[0]).setVisible(false);
}
