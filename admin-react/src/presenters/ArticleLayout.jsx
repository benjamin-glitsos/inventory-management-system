import styled from "styled-components";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import ContentMargins from "%/presenters/ContentMargins";

export default ({ title, breadcrumbs, children }) => (
    <MarginBottom>
        <ContentMargins maxWidth="800px">
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            >
                {title}
            </PageHeader>
            {children}
        </ContentMargins>
    </MarginBottom>
);

const MarginBottom = styled.div`
    margin-bottom: 28px;
`;
