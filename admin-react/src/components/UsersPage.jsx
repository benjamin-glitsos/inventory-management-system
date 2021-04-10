import { createContext, useContext } from "react";
import { titleCase } from "title-case";
import PageContainer from "%/containers/PageContainer";
import ListContainer from "%/containers/ListContainer";
import PageLayout from "%/presenters/PageLayout";
import List from "%/components/List";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const context = useContext(Context);

    const nameSingular = "user";
    const namePlural = "users";
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A list of users who can log into the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const listContainer = ListContainer({
        apiPath: `v1/${slug}/`,
        defaultState: {
            request: {
                body: {}
            },
            response: {
                data: {
                    items: []
                },
                errors: []
            },
            isLoading: false,
            selected: []
        },
        headContentColumns: [
            { key: "username", content: "Username", isSortable: true },
            { key: "first_name", content: "Name", isSortable: true },
            {
                key: "email_address",
                content: "Email Address",
                isSortable: true
            }
        ],
        rowTransform: row =>
            transform(row, {
                created_at: friendlyDate,
                edited_at: d => friendlyDate(fromMaybe(d))
            })
    });

    const usersPageContext = {
        nameSingular,
        namePlural,
        title,
        ...pageContainer,
        ...listContainer
    };

    return (
        <Provider value={usersPageContext}>
            <PageLayout
                title={usersPageContext.metaTitle}
                description={usersPageContext.description}
            >
                <List context={usersPageContext} />
            </PageLayout>
        </Provider>
    );
};
