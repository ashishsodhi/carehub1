import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/project-template">
      Project Template
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/project">
      Project
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/project-participant">
      Project Participant
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/recipient">
      Recipient
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/concerns">
      Concerns
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/responsibilities">
      Responsibilities
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/item-participant">
      Item Participant
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/recipient-item">
      Recipient Item
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/task">
      Task
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/document">
      Document
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/message">
      Message
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/message-item">
      Message Item
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
