import { createContext, useContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import { titleCase } from "title-case";
import readme from "%/assets/README.md";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import GithubButton from "%/components/GithubButton";
import ImagesLightbox from "%/components/ImagesLightbox";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const context = useContext(Context);

    const nameSingular = "readme";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const pageContext = { ...pageContainer };

    return (
        <Provider value={pageContext}>
            <PageLayout
                title={pageContext.metaTitle}
                description={pageContext.description}
            >
                <ArticleLayout
                    title={pageContext.title}
                    breadcrumbs={[pageContext.homeBreadcrumb]}
                >
                    <ReactMarkdown source={readme} />
                    <h2>Codebase</h2>
                    <GithubButton />
                    <h2>Database Schema</h2>
                    <DatabaseSchemaStyles>
                        <ImagesLightbox
                            images={[
                                {
                                    src:
                                        process.env.PUBLIC_URL +
                                        "/images/er-diagram.svg",
                                    alt: `Entity-Relationship diagram of the database of the ${process.env.PROJECT_ABBREV}`
                                }
                            ]}
                        />
                    </DatabaseSchemaStyles>
                </ArticleLayout>
            </PageLayout>
        </Provider>
    );
};

const DatabaseSchemaStyles = styled.div`
    margin-top: 28px;
`;
