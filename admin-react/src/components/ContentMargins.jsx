import React from "react";
import { Grid, GridColumn } from "@atlaskit/page";
import { gridSize } from "@atlaskit/theme";
import styled from "styled-components";

export default ({ maxWidth, children }) => {
    return <Margins maxWidth={maxWidth}>{children}</Margins>
}

const Margins = styled.div`
    margin: 0 auto;
    max-width: ${props => props.maxWidth};
    padding: 0 3em;
`;
